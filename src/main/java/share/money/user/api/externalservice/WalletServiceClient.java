package share.money.user.api.externalservice;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import share.money.user.api.controller.model.response.OperationStatusModel;
import share.money.user.api.controller.model.response.WalletRest;
import share.money.user.api.externalservice.model.WalletCreationRequestModel;

@FeignClient(name = "wallet-ws", fallbackFactory = WalletFallbackFactory.class)
public interface WalletServiceClient {

    @GetMapping(path = "/wallet/{userId}")
    WalletRest getUserWallet(@PathVariable String userId);

    @PostMapping(path = "wallet/{userId}")
    WalletRest createUserWallet(@PathVariable String userId, @RequestBody WalletCreationRequestModel walletCreationRequestModel);

    @DeleteMapping(path = "wallet/{userId}")
    OperationStatusModel deleteWallet(@PathVariable String userId);
}

@Component
class WalletFallbackFactory implements FallbackFactory<WalletServiceClient> {

    @Override
    public WalletServiceClient create(Throwable throwable) {
        return new WalletFallback(throwable);
    }
}

class WalletFallback implements WalletServiceClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Throwable throwable;

    public WalletFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public WalletRest getUserWallet(String userId) {

        logger.error(throwable.getLocalizedMessage());

        WalletRest walletRest = new WalletRest();
        walletRest.setTotal(0.0);
        walletRest.setUserId("Error");
        return walletRest;
    }

    @Override
    public WalletRest createUserWallet(String userId, WalletCreationRequestModel walletCreationRequestModel) {

        logger.error(throwable.getLocalizedMessage());
        WalletRest walletRest = new WalletRest();
        walletRest.setTotal(0.0);
        walletRest.setUserId("Error");
        return walletRest;
    }

    @Override
    public OperationStatusModel deleteWallet(String id) {
        logger.error(throwable.getLocalizedMessage());
        return new OperationStatusModel(String.format("Failed. Wallet for user [%s] wasn't deleted", id), "Delete");
    }
}
