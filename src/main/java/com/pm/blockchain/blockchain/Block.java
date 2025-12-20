package com.pm.blockchain.blockchain;

import com.pm.blockchain.transaction.Transaction;
import com.pm.blockchain.transaction.TransactionInput;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;

public class Block {

    private String hash;
    private final String previousHash;
    private final long timeStamp;
    private int nonce;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.

    //Block Constructor.
    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(previousHash
                + Long.toString(timeStamp)
                +Integer.toString(nonce)
                + merkleRoot);

    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void mineBlock(int difficulty, PublicKey minerPublicKey) {
        Transaction rewardTransaction = new Transaction(null,minerPublicKey,TestChain.miningReward,new ArrayList<TransactionInput>());
        addTransaction(rewardTransaction);
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        hash = calculateHash();
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined! : " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) return false;
        if((!transaction.processTransaction())) {
            System.out.println("Transaction failed to process. Discarded.");
            return false;
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}