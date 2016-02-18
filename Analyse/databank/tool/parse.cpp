#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

struct Traject{
    string letter;
    string naam;
    string lengte;
    int is_active=1;
};

int main()
{
    ifstream myfile;
    myfile.open("trajecten.txt");
    string lijn;
    vector<Traject> trajecten;
    //The header ID
    getline(myfile,lijn);
    getline(myfile,lijn);
    while(lijn != "Name")
        getline(myfile,lijn);
    getline(myfile,lijn);
    while(lijn != "Length")
    {
        Traject traject;
        traject.naam = lijn;
        trajecten.push_back(traject);
        getline(myfile,lijn);
    }
    getline(myfile,lijn);
    int i = 0;
    while(lijn != "Letter")
    {
        trajecten.at(i).lengte = lijn;
        i++;
        getline(myfile,lijn);
    }
    getline(myfile,lijn);
    i = 0;
    while(!myfile.eof())
    {
        trajecten.at(i).letter = lijn;
        i++;
        getline(myfile,lijn);
    }
    myfile.close();
    //Loop through vector
    for(auto const& value: trajecten) {
        cout<<"('"<<value.letter<<"','"<<
            value.naam<<"',"<<value.lengte<<","<<value.is_active<<"),"<<endl;
    }
}
