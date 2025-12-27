package com.pm.blockchain;

import com.google.gson.GsonBuilder;
import com.pm.blockchain.blockchain.Block;
import com.pm.blockchain.blockchain.Chain;
import com.pm.blockchain.blockchain.StringUtil;
import com.pm.blockchain.transaction.Transaction;
import com.pm.blockchain.transaction.TransactionOutput;
import com.pm.blockchain.wallet.Wallet;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

import static com.pm.blockchain.blockchain.Chain.isChainValid;
import static com.pm.blockchain.blockchain.TestChain.*;

public class Main {








    public static Wallet minerWallet;
    public static Wallet walletA;
    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        //Create the new wallets
        minerWallet = new Wallet();
        walletA = new Wallet();
        //Test public and private keys
        System.out.println("Private and public keys of wallet a:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        System.out.println("Private and public keys of miner wallet :");
        System.out.println(StringUtil.getStringFromKey(minerWallet.privateKey));
        System.out.println(StringUtil.getStringFromKey(minerWallet.publicKey));

        //testing the creation of blocks

        System.out.println("Creation of the genesis bloc :");
        Block genesisBlock = new Block("0",UTXOs,miningReward,minimumTransaction);
        genesisBlock.mineBlock(difficulty,minerWallet.publicKey);
        blockchain.add(genesisBlock);
        System.out.println("miner balance : " +minerWallet.getBalance());


        //testing transaction between wallets
        System.out.println("Creating transaction");
        Transaction tx1 = minerWallet.sendFunds(walletA.publicKey, 1.5f);
        System.out.println("Add the transaction to the block and then mining it");
        Block block1 = new Block(genesisBlock.getHash(),UTXOs,miningReward,minimumTransaction);
        block1.addTransaction(tx1);
        block1.mineBlock(difficulty,walletA.publicKey);
        blockchain.add(block1);
        System.out.println("miner balance : " +minerWallet.getBalance());
        System.out.println("walletA balance : " +walletA.getBalance());
        System.out.println("\n--- DÉBUT DE LA TENTATIVE DE FRAUDE ---");

        System.out.println("\n--- STARTING FRAUD SIMULATION ---");

// 1. The hacker tries to modify an old transaction in the blockchain
// He changes the value from 1.5 to 1000.0 in the first real block
        blockchain.get(1).transactions.get(0).outputs.get(0).value = 1000f;
        System.out.println("Hacker action: Modified a past transaction value to 1000.0");

// 2. We run the security audit
        System.out.println("Running security audit...");
        boolean isSystemSecure = Chain.isChainValid();

        if(!isSystemSecure) {
            System.out.println("SUCCESS: The fraud was detected and blocked by the system!");
        } else {
            System.out.println("CRITICAL ERROR: The fraud was NOT detected. Security breach!");
        }


        //Create a test transaction from WalletA to walletB
        //Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
        //transaction.generateSignature(walletA.privateKey);
        //Verify the signature works and verify it from the public key
//        System.out.println("Is signature verified");
//        System.out.println(transaction.verifySignature());




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