package de.ass37.examples.services;

import de.ass37.examples.models.DepositModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepositService {
    public DepositModel addToDepositByUserId(DepositModel depositModel) {
        List<Long> coins = new ArrayList<>();
        coins.add(5l);
        coins.add(10l);
        coins.add(20l);
        coins.add(50l);
        coins.add(100l);

        if(!coins.contains(depositModel.getCoin())) {

        } else {

        }
        return depositModel;
    }

    /*
        const user = findUserById(userId)
    const {coins} = deposit
    const coinValues = [5, 10, 20, 50, 100];
    if (!Array.isArray(coins)) {
        return  { error: 'Coins must be an array'};
    }
    if (coins.some(c => !coinValues.includes(c))) {
        return {error: 'Invalid coin value'};
    }
    if (!user || user.role !== 'buyer') {
        return {error: 'User not found' };
    }
    user.deposit += coins.reduce((acc, curr) => acc + curr, 0);
    return user
     */

    public DepositModel getDeposit() {
        DepositModel depositModel = new DepositModel();
        return depositModel;
    }
}
