# Simple Java Blockchain (UTXO Model)

A functional blockchain implementation built in Java, demonstrating the core principles of decentralized ledgers. This project focuses on **Proof of Work**, **Merkle Trees**, and the **UTXO (Unspent Transaction Output) model**, providing a robust foundation for understanding how digital currencies like Bitcoin operate.

---

## 💡 Inspiration
This project was inspired by the excellent tutorial: 
**[Creating a Simple Blockchain in Java](https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa)** by Kass on Medium. It follows the architectural patterns for transaction management and security presented in that guide.

---

## 🚀 Key Features

* **UTXO Transaction Model**: Funds are managed through unspent outputs rather than simple account balances.
* **Proof of Work (PoW)**: A mining system that requires computational effort to secure the network and validate blocks.
* **Modular Architecture**: Fully decoupled classes. `Block` and `Transaction` no longer rely on global static variables, making the code testable and reusable.
* **Digital Signatures**: Uses **ECDSA** (Elliptic Curve Digital Signature Algorithm) to ensure that only the owner of funds can authorize a transfer.
* **Merkle Tree Integration**: Transactions within a block are summarized into a Merkle Root for efficient data integrity verification.
* **Tamper-Evident Chain**: Every block is cryptographically linked to the previous one via hashes, making history immutable.
* **Advanced Chain Validation**: The `isChainValid` engine reconstructs the entire financial history from scratch to detect any monetary or structural fraud.



---

## 🏗️ Architecture & Logic

### 1. Wallet & Security
* **Key Generation**: Each wallet generates a Public/Private key pair using the Bouncy Castle provider.
* **Balance Calculation**: Balances are dynamically calculated by scanning the global UTXO list for outputs belonging to the wallet's public key.
* **Signature**: Private keys are used to sign transaction data, while public keys act as addresses.
* **Decoupling**: Wallets are independent of the chain state until a transaction is processed.

### 2. Dependency Injection
Unlike basic implementations, this version uses **Constructor and Method Injection**:
* **Blocks** are initialized with their specific context (UTXO set, Reward, Difficulty).
* **Transactions** receive the current valid UTXO set as a parameter during processing.
This prevents "hidden dependencies" and ensures that the data flow is explicit.

### 3. Transaction Processing
* **Inputs & Outputs**: Transactions consist of `TransactionInput` (references to previous funds) and `TransactionOutput` (the new distribution of value).
* **The Change Mechanism**: If the total value of inputs exceeds the amount to be sent, the system automatically creates a "change" output sent back to the sender.
* **UTXO Cleanup**: Once spent, an output is removed from the global unspent list to prevent double-spending.

### 4. Mining (Proof of Work)
* **Hashing**: Blocks use **SHA-256** to hash data including the timestamp, previous hash, and Merkle root.
* **Difficulty**: The `mineBlock` method requires a `nonce` to be found such that the block's hash starts with a specific number of zeros.

### 5. Validation Engine (The Consensus Audit)
The `Chain.isChainValid()` method acts as the "Supreme Judge" of the network by performing a 4-layer check:
1.  **Block Hash Check**: Verifies that the block's content matches its hash.
2.  **PoW Check**: Ensures the block was mined with the correct difficulty.
3.  **Merkle Audit**: Recalculates the Merkle Root from the transaction list to prevent data tampering.
4.  **Financial Audit**: Reconstructs a temporary UTXO map from the Genesis block to the latest block to verify:
    * Signatures are valid.
    * Inputs exist in the history (Double-spend prevention).
    * **Monetary Policy**: Mining rewards match the protocol rules ($Output = Reward$).
    * **Value Conservation**: Total $Inputs = Outputs$ for all standard transactions.

## 🚦 How to use

### Initialize & Mine
1.  **Initialize the Security Provider**: `Security.addProvider(new BouncyCastleProvider());`
2.  **Create the Genesis Block**: Pass the initial UTXO map and reward rules to the constructor.
3.  **Mine**: `block.mineBlock(difficulty, minerPublicKey);`

### Process Transactions
1.  **Create a transaction**: `Transaction tx = walletA.sendFunds(recipient, amount);`
2.  **Add to block**: `block.addTransaction(tx);` (This automatically updates the Merkle Root and Block Hash).

### Security Testing (Hacker Simulation)
The system is designed to detect fraud. You can test it by manually modifying a value in the blockchain:
```java
// Simulation: Hacker modifies a past transaction
blockchain.get(1).transactions.get(0).outputs.get(0).value = 1000f;

// Audit will return false and log: 
// "Merkle root mismatch" or "Monetary policy violation"
Chain.isChainValid();

