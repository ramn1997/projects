#include <iostream>
#include <unistd.h>

#include "dictionary.cpp"
#include "bruteforce.cpp"

using namespace std;


int main()
{

	int choice;
	
	cout<<"====================================="<<endl;
	cout<<"===========CrackIT'==========="<<endl;
	cout<<"====================================="<<endl;
	cout<<"==      1. Dictionary Attack       =="<<endl;
	cout<<"==      2. Brute Force Attack      =="<<endl;
	cout<<"====================================="<<endl;
	
	//cout<<endl;
	cout<<'\t'<<"Choice: ";
	cin>>choice;
	//cout<<endl;
	cout<<"====================================="<<endl<<endl;

	switch(choice)
	{
		case 1:
				dictionary(); 
				break;
		
		case 2: 
				bruteforce(); 
				break;
		default:
				cout<<"==                     Wrong Choice.                     =="<<endl;
				cout<<"==              Abrupt Exit in 3...2...1...              =="<<endl;
				break;
	}
	
	return 0;
}
