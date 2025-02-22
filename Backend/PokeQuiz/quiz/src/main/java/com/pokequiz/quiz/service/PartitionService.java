package com.pokequiz.quiz.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PartitionService {

    private final JdbcTemplate jdbcTemplate;
    private static final int PARTITION_COUNT = 2; // Adjust as needed
    private static final Logger log = LoggerFactory.getLogger(PartitionService.class);

    @Autowired
    public PartitionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void createPartitions() {
        try {
            log.info("🔥 Checking if partitioned table exists...");

            Integer tableExists = jdbcTemplate.queryForObject(
                    """
                    SELECT COUNT(*) 
                    FROM pg_partitioned_table pt
                    JOIN pg_class pc ON pt.partrelid = pc.oid
                    WHERE pc.relname = 'quiz_attempts'
                    """,
                    Integer.class
            );

            if (tableExists != null && tableExists > 0) {
                log.info("✅ Partitioned table already exists. Skipping creation.");
                return; // Partitioned table exists—skip creation
            }

            log.info("🔥 Creating main partitioned table...");
            String createMainTableSQL = """
            DROP TABLE IF EXISTS quiz_attempts Cascade;
            CREATE TABLE quiz_attempts (
                id BIGINT GENERATED ALWAYS AS IDENTITY,  -- ✅ Fixed auto-increment
                user_id BIGINT NOT NULL,
                question_id BIGINT NOT NULL,
                selected_answer VARCHAR(255) NOT NULL,
                is_correct BOOLEAN NOT NULL,
                start_time TIMESTAMP NOT NULL,
                end_time TIMESTAMP NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                PRIMARY KEY (user_id, id) -- ✅ Fixed: id must be after user_id
            ) PARTITION BY HASH (user_id);
        """;

            jdbcTemplate.execute(createMainTableSQL);
            log.info("✅ Main partitioned table created!");

            for (int i = 0; i < PARTITION_COUNT; i++) {
                String partitionSQL = String.format("""
                CREATE TABLE IF NOT EXISTS quiz_attempts_partition_%d 
                PARTITION OF quiz_attempts 
                FOR VALUES WITH (MODULUS %d, REMAINDER %d);
            """, i, PARTITION_COUNT, i);

                jdbcTemplate.execute(partitionSQL);
                log.info("✅ Partition {} created!", i);
            }

        } catch (Exception e) {
            log.error("❌ Error during partition creation: {}", e.getMessage(), e);
        }
    }



}
