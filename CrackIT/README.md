# CrackIT
A plain hashed password cracker using brute force or dictionary attack.

(for the hypothetical case that you, somehow, got an entire log of user accounts in your hands and you want to extract the passwords of it. you edgy haxxer. shame on you. i'm calling the local authorities.)

### Guide to compile and run:
`g++ -o main main.cpp -lcrypt`

### Limitations:
* only small latin letters and digits used for brute force attacks
* dictionary has a small portion of a full dictionary, as well as some common weak passwords and two already known passwords in the log

### Estimated times (based on impatience and poor math skills):
* **Brute Force attack:** _43h20m_
* **Dictionary attack:** _12h36m_
