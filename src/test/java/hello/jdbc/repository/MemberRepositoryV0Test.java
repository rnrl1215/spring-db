package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {
    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("member1", 100000);
        repositoryV0.save(member);

        Member findMember = repositoryV0.findById(member.getMemberId());
        log.info("findMember={}", member);
        log.info("member != findMember {}", member==findMember);
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}