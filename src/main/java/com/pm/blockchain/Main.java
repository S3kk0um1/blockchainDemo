package com.pm.blockchain;

import com.google.gson.GsonBuilder;
import com.pm.blockchain.blockchain.Block;
import com.pm.blockchain.blockchain.StringUtil;
import com.pm.blockchain.transaction.Transaction;
import com.pm.blockchain.transaction.TransactionOutput;
import com.pm.blockchain.wallet.Wallet;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import static com.pm.blockchain.blockchain.Chain.isChainValid;

public class Main {







    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;
    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        walletA = new Wallet();
        walletB = new Wallet();
        //Test public and private keys
        System.out.println("Private and public keys of wallet a:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
        //Create a test transaction from WalletA to walletB
        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        transaction.generateSignature(walletA.privateKey);
        //Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());




        /*
        blockchain.add(new Block("First block of the blockchain ", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Second block of the blockchain ", blockchain.getLast().getHash()));
        System.out.println("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Third block of the blockchain ", blockchain.getLast().getHash()));
        System.out.println("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid(blockchain,difficulty));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
        */


    }
}