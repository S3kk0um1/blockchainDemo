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
* **Digital Signatures**: Uses **ECDSA** (Elliptic Curve Digital Signature Algorithm) to ensure that only the owner of funds can authorize a transfer.
* **Merkle Tree Integration**: Transactions within a block are summarized into a Merkle Root for efficient data integrity verification.
* **Tamper-Evident Chain**: Every block is cryptographically linked to the previous one via hashes, making history immutable.



---

## 🏗️ Architecture & Logic

### 1. Wallet & Security
* **Key Generation**: Each wallet generates a Public/Private key pair using the Bouncy Castle provider.
* **Balance Calculation**: Balances are dynamically calculated by scanning the global UTXO list for outputs belonging to the wallet's public key.
* **Signature**: Private keys are used to sign transaction data, while public keys act as addresses.

### 2. Transaction Processing
* **Inputs & Outputs**: Transactions consist of `TransactionInput` (references to previous funds) and `TransactionOutput` (the new distribution of value).
* **The Change Mechanism**: If the total value of inputs exceeds the amount to be sent, the system automatically creates a "change" output sent back to the sender.
* **UTXO Cleanup**: Once spent, an output is removed from the global unspent list to prevent double-spending.



### 3. Mining (Proof of Work)
* **Hashing**: Blocks use **SHA-256** to hash data including the timestamp, previous hash, and Merkle root.
* **Difficulty**: The `mineBlock` method requires a `nonce` to be found such that the block's hash starts with a specific number of zeros.


### How to use
1.  **Initialize**: Set up the blockchain and global UTXO map in `TestChain`.
2.  **Transactions**: Create wallets and use `walletA.sendFunds(walletB.publicKey, amount)`.
3.  **Mine**: Add the transaction to a block and call `mineBlock(difficulty)`.
4.  **Verify**: Use `Chain.isChainValid()` to ensure the integrity of the ledger.

