# Reliable Data Transfer Algorithm
> Project was developed as a simulation of reliable data transfer or reliable segment delivery.
> 
## :information_source: Information 

Reliable data transfer protocol is done on a reliable channel, but it has the network layer below it: an unreliable channel. For example, TCP is a reliable data transfer protocol implemented over an untrusted end-to-end network layer (IP).


## ⚠️ Prerequisite
[![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/br/java/technologies/javase-downloads.html) >= 11 


## :rocket: Installation

![](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

```sh
git clone https://github.com/RamonBecker/ReliableDataTransferAlgorithm.git
```

![](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)


```sh
git clone https://github.com/RamonBecker/ReliableDataTransferAlgorithm.git
or install github https://desktop.github.com/ 

```

## :zap: Technologies	

- Java


## :memo: Developed features

- [x] Checksum to detect errors
- [x]  Acknowledgments (ACKs): receiver explicitly notifies the sender that the packet has been received correctly
- [x]  Negative acknowledgments (NAKs): receiver explicitly notifies the transmitter that the packet has errors
- [x]  Transmitter resends the packet upon receipt of a NAK
- [x]  Transmitter adds sequence number to each packet
- [x] Transmitter resends the last packet if ACK / NAK is lost
- [x] Receiver discards (does not pass to the application) duplicate packets



## :technologist:	 Author

By Ramon Becker 👋🏽 Get in touch!



[<img src='https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/github.svg' alt='github' height='40'>](https://github.com/RamonBecker)  [<img src='https://cdn.jsdelivr.net/npm/simple-icons@3.0.1/icons/linkedin.svg' alt='linkedin' height='40'>](https://www.linkedin.com/in/https://www.linkedin.com/in/ramon-becker-da-silva-96b81b141//)
![Gmail Badge](https://img.shields.io/badge/-ramonbecker68@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:ramonbecker68@gmail.com)



