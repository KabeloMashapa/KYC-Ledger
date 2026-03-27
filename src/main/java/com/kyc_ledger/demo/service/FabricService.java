package com.kyc_ledger.demo.service;
import com.kyc_ledger.demo.exception.FabricException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FabricService {

    @Value("${fabric.channel.name}")
    private String channelName;
    @Value("${fabric.chaincode.name}")
    private String chaincodeName;

    public String submitKycToBlockchain(String kycId,String kycHash) {
        try{
            System.out.println("Submitting KYC to blockchain: " + kycId);
            return "TX-" + java.util.UUID.randomUUID().toString().toUpperCase();

        }
        catch (Exception e) {
            throw new FabricException("Failed to submit KYC to blockchain", e);

        }
    }

}
