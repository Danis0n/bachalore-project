package ru.fp.billingservice.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.fp.billingservice.dto.Report;
import ru.fp.billingservice.entity.decriptor.DescriptorType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ReportDao {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public List<Report> findResultsWithFilters(String bic, Timestamp startDate, Timestamp endDate) {
        StringBuilder sql = new StringBuilder().append("""
                SELECT results.id, results.commission_amount, results.status, results.date,
                       t.uuid, s.bic as senderBic, r.bic as receiverBic, d.name, d.rate, d.type
                           FROM results
                                     JOIN descriptor d on d.id = results.descriptor_id
                                     JOIN participant s on s.id = results.sender_participant
                                     JOIN participant r on r.id = results.receiver_participant
                                     JOIN transaction t on t.id = results.transaction_id
                WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

        if (bic != null) {
            sql.append("AND ( s.bic = ? OR r.bic = ?)");
            params.add(bic);
            params.add(bic);
        }

        if (startDate != null) {
            sql.append("AND ( date >= ?)");
            params.add(startDate);
        }

        if (endDate != null) {
            sql.append("AND ( date <= ?)");
            params.add(endDate);
        }

        return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            String descriptor = rs.getString("name") + " "
                    + rs.getString("rate");

            String type = rs.getString("type");

            if (DescriptorType.PERCENT.name().equals(type)) {
                descriptor += "%";
            }

            return Report.builder()
                    .id(rs.getLong("id"))
                    .date(rs.getTimestamp("date"))
                    .status(rs.getString("status"))
                    .commission(rs.getDouble("commission_amount"))
                    .sender(rs.getString("senderBic"))
                    .receiver(rs.getString("receiverBic"))
                    .descriptor(descriptor)
                    .transaction(rs.getString("uuid"))
                    .build();
        }, params.toArray());
    }

}
