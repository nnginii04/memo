package com.meta.memo.controller;

import com.meta.memo.domain.Memo;
import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.dto.MemoResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("api/memos")
public class MemoController {

    // JDBC를 통한 MySQL 데이터베이스 연결
    private final JdbcTemplate jdbcTemplate;

    public MemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto) {
        Memo newMemo = new Memo(memoRequestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO memo(username, contents) VALUES (?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement =
                    con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newMemo.getUsername());
            preparedStatement.setString(2, newMemo.getContents());
            return preparedStatement;
        }, keyHolder);

        return new MemoResponseDto(newMemo);
    }

    // READ (전체 조회)
    @GetMapping
    public List<MemoResponseDto> getMemos() {
        String sql = "SELECT * FROM memo";

        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                return new MemoResponseDto(id, username, contents);
            }
        });
    }

    // UPDATE
    @PutMapping("/{id}")
    public Long updateMemo(@PathVariable Long id,
                           @RequestBody MemoRequestDto memoRequestDto) {

        Memo foundMemo = findById(id);

        if (foundMemo == null) {
            throw new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다.");
        }

        String sql = "UPDATE memo SET username = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                memoRequestDto.getUserName(),
                memoRequestDto.getContents(),
                id
        );

        return id;
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Long deleteMemo(@PathVariable Long id) {

        Memo foundMemo = findById(id);

        if (foundMemo == null) {
            throw new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다.");
        }

        String sql = "DELETE FROM memo WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return id;
    }

    // 공용 메서드
    private Memo findById(Long id) {
        String sql = "SELECT * FROM memo WHERE id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                Memo memo = new Memo();
                memo.setId(rs.getLong("id"));
                memo.setUsername(rs.getString("username"));
                memo.setContents(rs.getString("contents"));
                return memo;
            }
            return null;
        }, id);
    }
}