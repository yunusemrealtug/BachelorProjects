#include <iostream>
#include <cstdio>
#include <memory>
#include <string>
#include <array>
#include <sstream>
#include <queue>
using namespace std;


std::string exec(const char* cmd) { //this function takes command and instead of printing, returns it without \n.
    std::array<char, 128> buffer;
    std::string result;
    std::unique_ptr<FILE, decltype(&pclose)> pipe(popen(cmd, "r"), pclose);
    while (fgets(buffer.data(), buffer.size(), pipe.get()) != nullptr) {
        result += buffer.data();
    }
    result.erase(std::prev(result.end()));
    return result;
}

bool searchInQueue (queue<string> queue, string element) { //this function searchs element in queue.
    string elemqueue;
    bool res=false;
    if (!queue.empty()) {
        string first=queue.front();
        queue.pop();
        queue.push(first);
        elemqueue=queue.front();
        if (elemqueue==element) {
                res=true;
        }
        while (first!=elemqueue){//until finding the first element it will pop and push.
            queue.pop();
            queue.push(elemqueue);
            elemqueue=queue.front();
            if (elemqueue==element) {//if element is found in queue, it will return true.
                res=true;
            }
        }
        return res;
    }
    else {
        return res; //if queue empty, the element won't be found, so false returns.
    }
}


int main () {
    string command;
    string didcommand;
    string username;
    string token;
    string rcommand;
    string file;
    string blank;
    string destination;
    string three=" >>> ";
    queue<string> history;

    int line;
    const char space=' ';
    username=exec("whoami"); //username is assigned with whoami
    cout <<username<< three;
    getline(cin, command);
    while (command!="exit") {
        didcommand="dididothat \""+command+"\""; //concetanating with dididothat enables us compare the when dididothat implemented.
        if (command=="listdir") {
            history.push(didcommand); //history is queue which last 15 commands are hold
            if (history.size()>15) {
                history.pop();
            }
            system("ls");
        }
        else if (command=="mycomputername") {
            history.push(didcommand);
            if (history.size()>15) {
                history.pop();
            }
            system("whoami");
        }
        else if (command=="whatsmyip") {
            history.push(didcommand);
            if (history.size()>15) {
                history.pop();
            }
            system("hostname -I");
        }
        else if (command=="hellotext") {
            history.push(didcommand);
            if (history.size()>15) {
                history.pop();
            }
            system("gedit");
        }
        else { //the others were one word commands, now looking for multiple words
            stringstream command1(command);
            getline(command1, rcommand, ' '); //the command 
            getline(command1, file, ' ');  //the second word 
            if (rcommand=="printfile") {
                history.push(didcommand);
                if (history.size()>15) {
                    history.pop();
                }
                if (getline(command1, blank, ' ')){
                    getline(command1, destination, ' ');
                    file=file+" "+blank+" "+destination;
                    system(("cat " + file).c_str()); //copying with cat
                }
                else {
                    string fullCommand="wc -l < "+file;
                    const char* res1=fullCommand.c_str();
                    line=stoi(exec(res1)); //calculating number of lines in text
                    int count=0;
                    while (line!=count) {
                        count=count+1;
                        string strcount=to_string(count);
                        string headCommand="head -n "+strcount+" "+file+"| tail -1";
                        const char* res2=headCommand.c_str();
                        string newLine=exec(res2); //to delete endline and /r output is assigned
                        newLine.erase(std::prev(newLine.end())); // /r deleted.
                        cout<<newLine;
                        getline(cin, command); //expects enter, what command is not important
                    }
                    string headCommand="tail -1 "+file; //to print last line
                    const char* res2=headCommand.c_str();
                    system(res2);
                    getline(cin, command);
                }
                
            }
            else if (rcommand=="dididothat") {
                if (searchInQueue(history, command)==true) {//searchs command in history
                    cout<<"Yes\r\n";
                }
                else {
                    cout<<"No\r\n";
                }
            }   
              
        }
        cout <<username<< three;
        getline(cin, command);
    }

    return 0;
}

