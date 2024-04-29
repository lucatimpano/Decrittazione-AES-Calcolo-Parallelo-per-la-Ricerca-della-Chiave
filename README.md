# Decrittazione-AES-Calcolo-Parallelo-per-la-Ricerca-della-Chiave

## Overview

This repository contains Java code for a multi-threaded AES encryption key search tool. The tool is designed to decrypt a given file encrypted using AES encryption with an unknown key. It utilizes parallel processing to speed up the search process by distributing the workload across multiple threads.

## Features

- **Multi-threaded Search**: The program spawns multiple threads to concurrently search for the encryption key, increasing efficiency.
- **AES Decryption**: Uses the Java Cryptography Extension (JCE) to perform AES decryption on the encrypted file.
- **Interruptible Search**: The search process can be interrupted at any time, allowing for graceful termination of the program.
- **Progress Tracking**: Displays the progress of the search operation, indicating the percentage completed and the name of each thread.

## Usage

To use the tool, follow these steps:

1. Compile the Java files using a Java compiler.
2. Run the `SearchKeyConc` class, which initiates the search process.
3. Monitor the console output for progress updates and the decrypted text (if successful).

## Requirements

- Java Development Kit (JDK) installed on your system.
- Basic understanding of Java programming.
