package com.pm.blockchain.blockchain;

import com.pm.blockchain.transaction.Transaction;
import com.pm.blockchain.transaction.TransactionInput;
import com.pm.blockchain.transaction.TransactionOutput;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Block {

    private String hash;
    private final String previousHash;
    private final long timeStamp;
    private int nonce;
    public String merkleRoot;
    private HashMap<String, TransactionOutput> utxoRef;
    private float reward;
    private float minTx;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.

    //Block Constructor.
    public Block(String previousHash,HashMap<String, TransactionOutput> utxoRef, float reward, float minTx) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
        this.utxoRef = utxoRef;
        this.reward = reward;
        this.minTx = minTx;
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
        Transaction rewardTransaction = new Transaction(null,minerPublicKey,reward,new ArrayList<TransactionInput>());
        addTransaction(rewardTransaction);
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined! : " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) return false;
        if((!transaction.processTransaction(utxoRef, reward, minTx))) {
            System.out.println("Transaction failed to process. Discarded.");
            return false;
        }

        transactions.add(transaction);
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        hash = calculateHash();
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}