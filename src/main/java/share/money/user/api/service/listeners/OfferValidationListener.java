package share.money.user.api.service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import share.money.commons.events.UserValidationRequestEvent;
import share.money.commons.events.UserValidationResponseEvent;
import share.money.user.api.config.JmsConstants;
import share.money.user.api.service.BusinessException;
import share.money.user.api.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfferValidationListener {

    private final UserService userService;
    private final AmqpTemplate amqpTemplate;

    @RabbitListener(queues = JmsConstants.USER_VALIDATE_REQUEST)
    public void listenForUserValidationResponse(UserValidationRequestEvent requestEvent) {

        try {
            userService.getUserById(requestEvent.getOfferDto().getUserId());
            amqpTemplate.convertAndSend(JmsConstants.USER_EXCHANGE, JmsConstants.USER_VALIDATE_RESPONSE_KEY,
                    UserValidationResponseEvent.builder().offerDto(requestEvent.getOfferDto()).validationErrorExist(false).build());

            log.debug(String.format("User validation for offerId  [%s] is [%s]", requestEvent.getOfferDto().getOfferId(), false));

        } catch (BusinessException e) {
            amqpTemplate.convertAndSend(JmsConstants.USER_EXCHANGE, JmsConstants.USER_VALIDATE_RESPONSE_KEY,
                    UserValidationResponseEvent.builder().offerDto(requestEvent.getOfferDto()).validationErrorExist(true).validationErrorReason(e.getMessage()).build());
            log.debug(String.format("User validation for offerId  [%s] is [%s]", requestEvent.getOfferDto().getOfferId(), true));
        }
    }
}
