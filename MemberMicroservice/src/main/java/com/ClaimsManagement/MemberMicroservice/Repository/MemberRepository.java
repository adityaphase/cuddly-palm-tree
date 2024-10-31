package com.ClaimsManagement.MemberMicroservice.Repository;

import com.ClaimsManagement.MemberMicroservice.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
