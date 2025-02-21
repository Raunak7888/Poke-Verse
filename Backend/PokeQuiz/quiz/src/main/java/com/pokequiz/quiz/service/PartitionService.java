package com.pokequiz.quiz.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PartitionService {

    private final JdbcTemplate jdbcTemplate;
    private static final int PARTITION_COUNT = 2; // 🔥 Adjust as needed
    private static final Logger log = LoggerFactory.getLogger(PartitionService.class);


    @Autowired
    public PartitionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createPartitions() {
        try {
            log.info("🔥 Dropping old table if it exists...");
            jdbcTemplate.execute("DROP TABLE IF EXISTS quiz_attempts CASCADE;");

            log.info("🔥 Creating main partitioned table...");
            String createMainTableSQL = """
            CREATE TABLE quiz_attempts (
                id SERIAL,
                user_id BIGINT NOT NULL,
                quiz_id BIGINT NOT NULL,
                question_id BIGINT NOT NULL,
                selected_answer VARCHAR(255) NOT NULL,
                is_correct BOOLEAN NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                PRIMARY KEY (id, user_id)  -- ✅ Include user_id in PK!
            ) PARTITION BY HASH (user_id);
        """;

            jdbcTemplate.execute(createMainTableSQL);
            log.info("✅ Main partitioned table created!");

            for (int i = 0; i < PARTITION_COUNT; i++) {
                String partitionSQL = String.format("""
                CREATE TABLE quiz_attempts_partition_%d 
                PARTITION OF quiz_attempts 
                FOR VALUES WITH (MODULUS %d, REMAINDER %d);
            """, i,PARTITION_COUNT, i);

                jdbcTemplate.execute(partitionSQL);
                log.info("✅ Partition {} created!", i);
            }

        } catch (Exception e) {
            log.error("❌ Error during partition creation: {}", e.getMessage(), e);
        }
    }

}
