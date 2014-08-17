package com.summerclass.repository;

import com.summerclass.domain.Member;
import com.summerclass.reportdetails.MemberPendingEventsReportDetail;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao
{
    String getMemberId( String firstName );

    Member getMember( String memberId );

    int getMemberCount();

    void addMember( String firstName, String lastName );

    List<MemberPendingEventsReportDetail> getMemberPendingEventsReportDetails();

    boolean deleteMember( String memberId);
}
