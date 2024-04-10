package com.secfix.githubObserver.listener;

import com.secfix.githubObserver.config.RabbitConfig;
import com.secfix.githubObserver.model.ObservedRepo;
import com.secfix.githubObserver.service.impl.GithubClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ObservedRepoListener {

    private static final Logger logger = LoggerFactory.getLogger(ObservedRepoListener.class);

    @RabbitListener(queues = RabbitConfig.queueName)
    public void receiveMessage(final ObservedRepo observedRepo) {
        logger.info("---------------------------------------------");
        logger.info("Received update for observed repo: " + observedRepo.toString());
        logger.info("---------------------------------------------");
    }
}

