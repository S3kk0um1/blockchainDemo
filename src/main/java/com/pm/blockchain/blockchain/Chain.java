package com.pm.blockchain.blockchain;

import com.pm.blockchain.transaction.Transaction;
import com.pm.blockchain.transaction.TransactionInput;
import com.pm.blockchain.transaction.TransactionOutput;

import java.util.HashMap;

import static com.pm.blockchain.blockchain.TestChain.blockchain;
import static com.pm.blockchain.blockchain.TestChain.difficulty;

public class Chain {

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        HashMap<String, TransactionOutput> tempUTXOs = new HashMap<>();
        //loop through blockchain to check hashes:
        for(int i=0; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            //previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash())){
                System.out.println("Current Hashes not equal");
                return false;
            }

            //check if hash is solved
            if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }

            //check the merkleRoot
            if(!currentBlock.merkleRoot.equals(StringUtil.getMerkleRoot(currentBlock.transactions))){
                System.out.println("This block merkle root is not valid");
                return false;
            }

            if(i>0) {
                previousBlock = blockchain.get(i-1);
                //compare previous hash and registered previous hash
                if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                    System.out.println("Previous Hashes not equal");
                    return false;
                }
            }

            for(Transaction transaction : currentBlock.transactions) {
                if(transaction.inputs.isEmpty()){
                    if (transaction.outputs.getFirst().value!=TestChain.miningReward || transaction.outputs.size()!=1 ){
                        return false;
                    }

                }else {
                    if (transaction.verifySignature()){
                        float totalInputs=0 ;
                        float totalOutputs=0 ;
                        for(TransactionInput input : transaction.inputs) {
                            if(!tempUTXOs.containsKey(input.transactionOutputId)){
                                return false;
                            }
                            totalInputs+= tempUTXOs.get(input.transactionOutputId).value;
                        }
                        for(TransactionOutput output : transaction.outputs) {
                            totalOutputs+=output.value;
                        }
                        if(totalInputs !=totalOutputs){
                            return false;
                        }

                    }else{
                        return false;
                    }
                }

                for(TransactionInput input : transaction.inputs) {
                    tempUTXOs.remove(input.transactionOutputId);
                }
                for(TransactionOutput output : transaction.outputs) {
                    tempUTXOs.put(output.id, output);

                }

            }

        }
        return true;
    }
}
