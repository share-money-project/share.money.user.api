package share.money.user.api.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringRabbitConfig {

    public static final String EXCHANGE_WALLET = "wallet";
    public static final String QUEUE_WALLET_CREATE = "wallet_create_q";
    public static final String KEY_WALLET_CREATE = "wallet_create_k";

//    @Bean
//    public Queue createQueue() {
//        return new Queue(QUEUE_NAME, true);
//    }
//
//    @Bean
//    public Exchange fanoutExchange() {
//        return new DirectExchange(EXCHANGE_NAME, true, false);
//    }
//
//    @Bean
//    public Binding queueBinding() {
//        return new Binding(QUEUE_NAME, Binding.DestinationType.QUEUE, EXCHANGE_NAME, "", null);
//    }
}
