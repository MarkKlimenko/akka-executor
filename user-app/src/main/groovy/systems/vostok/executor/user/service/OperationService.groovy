package systems.vostok.executor.user.service

import groovy.util.logging.Slf4j
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@Slf4j
class OperationService {
    @Scheduled(cron = '0/10 0/1 * 1/1 * ?')
    void tellForOperation() {
        log.info('In tellForOperation')
    }
}
