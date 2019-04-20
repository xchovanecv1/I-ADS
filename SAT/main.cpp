// C++ implementation to find if the given
// expression is satisfiable using the
// Kosaraju's Algorithm
/*
 * https://www.geeksforgeeks.org/2-satisfiability-2-sat-problem/
 * https://jgke.fi/blog/posts/2015/03/2sat-implementation/
 */
#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
#include <fstream>
#include <string>

using namespace std;

const int MAX = 100000;

// data structures used to implement Kosaraju's
// Algorithm. Please refer
// http://www.geeksforgeeks.org/strongly-connected-components/
vector<int> adj[MAX];
vector<int> adjInv[MAX];
bool visited[MAX];
bool visitedInv[MAX];
stack<int> s;

// this array will store the SCC that the
// particular node belongs to
int scc[MAX];

// counter maintains the number of the SCC
int counter = 1;

// adds edges to form the original graph
void addEdges(int a, int b)
{
    adj[a].push_back(b);
}

// add edges to form the inverse graph
void addEdgesInverse(int a, int b)
{
    adjInv[b].push_back(a);
}

// for STEP 1 of Kosaraju's Algorithm
void dfsFirst(int u)
{
    if(visited[u])
        return;

    visited[u] = 1;

    for (int i=0;i<adj[u].size();i++)
        dfsFirst(adj[u][i]);

    s.push(u);
}

// for STEP 2 of Kosaraju's Algorithm
void dfsSecond(int u)
{
    if(visitedInv[u])
        return;

    visitedInv[u] = 1;

    for (int i=0;i<adjInv[u].size();i++)
        dfsSecond(adjInv[u][i]);

    scc[u] = counter;
}

// function to check 2-Satisfiability
bool is2Satisfiable(int n, int m, int a[], int b[])
{
    // adding edges to the graph
    for(int i=0;i<m;i++)
    {
        // variable x is mapped to x
        // variable -x is mapped to n+x = n-(-x)

        // for a[i] or b[i], addEdges -a[i] -> b[i]
        // AND -b[i] -> a[i]
        if (a[i]>0 && b[i]>0)
        {
            addEdges(a[i]+n, b[i]);
            addEdgesInverse(a[i]+n, b[i]);
            addEdges(b[i]+n, a[i]);
            addEdgesInverse(b[i]+n, a[i]);
        }

        else if (a[i]>0 && b[i]<0)
        {
            addEdges(a[i]+n, n-b[i]);
            addEdgesInverse(a[i]+n, n-b[i]);
            addEdges(-b[i], a[i]);
            addEdgesInverse(-b[i], a[i]);
        }

        else if (a[i]<0 && b[i]>0)
        {
            addEdges(-a[i], b[i]);
            addEdgesInverse(-a[i], b[i]);
            addEdges(b[i]+n, n-a[i]);
            addEdgesInverse(b[i]+n, n-a[i]);
        }

        else
        {
            addEdges(-a[i], n-b[i]);
            addEdgesInverse(-a[i], n-b[i]);
            addEdges(-b[i], n-a[i]);
            addEdgesInverse(-b[i], n-a[i]);
        }
    }

    // STEP 1 of Kosaraju's Algorithm which
    // traverses the original graph
    for (int i=1;i<=2*n;i++)
        if (!visited[i])
            dfsFirst(i);

    // STEP 2 pf Kosaraju's Algorithm which
    // traverses the inverse graph. After this,
    // array scc[] stores the corresponding value
    while (!s.empty())
    {
        int n = s.top();
        s.pop();

        if (!visitedInv[n])
        {
            dfsSecond(n);
            counter++;
        }
    }

    for (int i=1;i<=n;i++)
    {
        // for any 2 vairable x and -x lie in
        // same SCC
        //cout << "scc v:" << scc[i]  << " " << scc[i+n] << "\n";
        if(scc[i]==scc[i+n])
        {
            return false;
        }
    }

    // no such variables x and -x exist which lie
    // in same SCC
    return true;
}
size_t split(const std::string &txt, std::vector<std::string> &strs, char ch)
{
    size_t pos = txt.find( ch );
    size_t initialPos = 0;
    strs.clear();

    // Decompose statement
    while( pos != std::string::npos ) {
        strs.push_back( txt.substr( initialPos, pos - initialPos ) );
        initialPos = pos + 1;

        pos = txt.find( ch, initialPos );
    }

    // Add the last one
    strs.push_back( txt.substr( initialPos, std::min( pos, txt.size() ) - initialPos + 1 ) );

    return strs.size();
}

//  Driver function to test above functions
int main(int argc, char *argv[])
{
    int n = 0, m = 0;
    int *a, *b;

    if(argc != 2){
        cerr << "Specfiy input file as first argument!" << endl;
        return 0;
    }

    std::ifstream infile(argv[1]);
    std::string line;
    int ln = 0;
    while (std::getline(infile, line))
    {
        std::vector<std::string> dta;
        int cnt = split(line, dta, ' ' );
        if(ln == 0) {
            n = stoi(dta[0]);
            m = stoi(dta[1]);
            a = new int[m];
            b = new int[m];
        } else {
            if(cnt == 3) {
                a[ln-1] = stoi(dta[0]);
                b[ln-1] = stoi(dta[1]);
            } else {
                a[ln-1] = stoi(dta[0]);
                b[ln-1] = a[ln-1];
            }
        }
        ln++;
    }

    bool res = is2Satisfiable(n, m, a, b);

    if(!res) {
        cout << "NESPLNITELNA!" << endl;
        return 0;
    }
    cout << "SPLNITELNA" << endl;

    vector<vector<int>> scc_g(counter+1, vector<int>(0));

    for(int i=1; i <=n*2; i++) {
        int var_n = i;
        if(i > n) {
            var_n = - (i-n);
        }
        scc_g[scc[i]].push_back(var_n);
        //printf("scc %d -> %d'\n", scc[i], var_n);
    }

    vector<vector<int>>::iterator it;  // declare an iterator to a vector of strings

    vector<int> checked;
    int* values = new int[n+1];

    int scc_c = 0;
    for(it = scc_g.end(); it != scc_g.begin(); it--,scc_c++ ) {
        vector<int>::iterator itt;
        //printf("%d:", scc_c);
        for (itt = (*(it-1)).begin(); itt != (*(it-1)).end(); itt++) {
            int val = *itt;
            if(val != 0 && std::find(checked.begin(), checked.end(), abs(val)) == checked.end()) {

                int idx = abs(val);
                values[idx] = (idx == val);
                //printf("var: %d - > checked: %d (%d - %d)\n", idx, (idx==val), val, abs(val));
                checked.push_back(idx);
            }
            //printf("%d",*itt);
        }
        //printf("\n");
    }

    for(int v = 1; v <= n; v++ ) {
        if(values[v]) {
            cout << "PRAVDA" << endl;
        } else {
            cout << "NEPRAVDA" << endl;
        }
    }

    return 0;
}