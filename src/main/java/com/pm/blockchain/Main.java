package com.pm.blockchain;

import com.google.gson.GsonBuilder;
import com.pm.blockchain.blockchain.Block;

import java.util.ArrayList;

import static com.pm.blockchain.blockchain.Chain.isChainValid;

public class Main {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 6;
    public static void main(String[] args) {
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



    }
}