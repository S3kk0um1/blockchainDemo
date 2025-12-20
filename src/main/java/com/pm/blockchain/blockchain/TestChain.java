package com.pm.blockchain.blockchain;

import com.pm.blockchain.transaction.TransactionOutput;
import com.pm.blockchain.wallet.Wallet;

import java.util.ArrayList;
import java.util.HashMap;

public class TestChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); //list of all unspent transactions.
    public static int difficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;
    public static float minimumTransaction = 0.01f;

}
