#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <stdlib.h>

#define MAXCHAR 1000

int main()
{

    FILE *fp;
    char row[MAXCHAR];
    double times[4][9];

    fp = fopen("resultsMPI.csv", "r");
    int intervalSize[9] = {1000, 5000, 10000, 50000, 100000, 500000, 1000000, 10000000, 100000000};

    for (int i = 0; i < 1; i++)
    {
        fgets(row, MAXCHAR, fp);
    }
    for (int i = 0; i < 9; i++)
    {
        fgets(row, MAXCHAR, fp);
        double num = atof(row);
        times[0][i] = num;
    }
    for (int i = 0; i < 1; i++)
    {
        fgets(row, MAXCHAR, fp);
    }
    for (int i = 0; i < 9; i++)
    {
        fgets(row, MAXCHAR, fp);
        double num = atof(row);
        times[1][i] = num;
    }
    for (int i = 0; i < 1; i++)
    {
        fgets(row, MAXCHAR, fp);
    }
    for (int i = 0; i < 9; i++)
    {
        fgets(row, MAXCHAR, fp);
        double num = atof(row);
        times[2][i] = num;
    }
    for (int i = 0; i < 1; i++)
    {
        fgets(row, MAXCHAR, fp);
    }
    for (int i = 0; i < 9; i++)
    {
        fgets(row, MAXCHAR, fp);
        double num = atof(row);
        times[3][i] = num;
    }

    FILE *fpt; // file pointer to write results to csv file
    fpt = fopen("resultsMPI1.csv", "w+");
    fprintf(fpt, "M, T1(ms), T2(ms), T4(ms), T6(ms), S2, S4, S6\n");
    for (int i = 0; i < 9; i++)
    {
        fprintf(fpt, "%d, %f, %f, %f, %f, %f, %f, %f\n", intervalSize[i], times[0][i], times[1][i], times[2][i], times[3][i], times[0][i] / times[1][i], times[0][i] / times[2][i], times[0][i] / times[3][i]);
    }

    return 0;
}
