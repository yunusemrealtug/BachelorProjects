#include <stdio.h>
#include <math.h>
#include <omp.h>
#include <mpi.h>
#include <sys/time.h>
#include <stdint.h>
#include <stdlib.h>

int compare(const void *a, const void *b) // compare function for sorting
{
    return (*(int *)a - *(int *)b);
}

double currentTimeMillis() // function to get current time in milliseconds
{
    struct timeval time;
    gettimeofday(&time, NULL);
    double s1 = (time.tv_sec) * 1000;
    double s2 = ((double)time.tv_usec / 1000);
    return s1 + s2;
}

void parallel(int mypid, int size, int N)
{

    int *prime;
    int *localPrime = (int *)malloc((((N / 2) + 1) / size) * sizeof(int));
    // int localPrime[((N / 2) + 1) / size];

    int j;
    int k;
    int n;
    int quo, rem;
    int serialN;
    int broken = 0;
    int l;
    int totalCount = 0;
    prime = (int *)malloc(((N / 2) + 1) * sizeof(int));

    if (mypid == 0)
    {

        prime[0] = 2;
        n = 3;
        j = 1;
        prime[j] = n;
        serialN = sqrt(N);
        for (n = 5; n <= N; n += 2)
        {
            k = 1;
            quo = n / prime[k];
            rem = n % prime[k];
            if (rem != 0)
            {
                while (quo > prime[k])
                {
                    k += 1;
                    quo = n / prime[k];
                    rem = n % prime[k];
                    if (rem == 0)
                    {
                        broken = 1;
                        break;
                    }
                }
                if (broken == 0)
                {
                    j += 1;
                    prime[j] = n;
                    if (n > serialN)
                    {
                        break;
                    }
                }
                else
                {
                    broken = 0;
                }
            }
        }
        broken = 0;
        l = n + 2;
    }
    MPI_Bcast(&l, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(&j, 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Bcast(prime, j + 1, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Barrier(MPI_COMM_WORLD);

    int count = 0;
    for (int a = (l + 2 * mypid); a < N; a += size * 2)
    {
        k = 1;
        quo = a / prime[k];
        rem = a % prime[k];
        if (rem != 0)
        {
            while (quo > prime[k])
            {
                k += 1;
                quo = a / prime[k];
                rem = a % prime[k];
                if (rem == 0)
                {
                    broken = 1;
                    break;
                }
            }
            if (broken == 0)
            {

                localPrime[count] = a;
                count += 1;
            }
            else
            {
                broken = 0;
            }
        }
    }
    MPI_Barrier(MPI_COMM_WORLD);

    int *all_counts;
    if (mypid == 0)
    {
        all_counts = (int *)malloc(size * sizeof(int));
    }
    MPI_Gather(&count, 1, MPI_INT, all_counts, 1, MPI_INT, 0, MPI_COMM_WORLD);

    int *displs;
    if (mypid == 0)
    {
        displs = (int *)malloc(size * sizeof(int));
        displs[0] = 0;
        for (int i = 1; i < size; i++)
        {
            displs[i] = displs[i - 1] + all_counts[i - 1];
            totalCount += all_counts[i - 1];
        }
        totalCount += all_counts[size - 1];
    }

    MPI_Gatherv(localPrime, count, MPI_INT, prime + j + 1, all_counts, displs, MPI_INT, 0, MPI_COMM_WORLD);
    /*
    if (mypid == 0)
    {
        qsort(prime, j + totalCount + 1, sizeof(prime[0]), compare);
        for (int i = 0; i < j + totalCount + 1; i++)
        {
            printf("%d\n", prime[i]);
        }
    }
    */
    free(localPrime);
}

int main(int argc, char *argv[])
{

    FILE *fpt; // file pointer to write results to csv file
    fpt = fopen("resultsMPI.csv", "a+");

    int mypid;
    int size;
    int intervalSize[9] = {1000, 5000, 10000, 50000, 100000, 500000, 1000000, 10000000, 100000000};

    double time_taken;
    double t;

    MPI_Init(&argc, &argv); /* starts MPI */

    MPI_Comm_rank(MPI_COMM_WORLD, &mypid); /* get current process id */
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (mypid == 0)
    {
        fprintf(fpt, "M, T1(ms), T2(ms), T4(ms), T6(ms), S2, S4, S6\n");
    }

    for (int i = 0; i < 9; i++)
    {
        if (mypid == 0)
        {

            t = currentTimeMillis();
        }

        parallel(mypid, size, intervalSize[i]);

        if (mypid == 0)
        {
            t = currentTimeMillis() - t;
            printf("Time taken for parallel: %f\n", t);
            fprintf(fpt, "%f \n", t);
        }
    }

    MPI_Finalize();

    fclose(fpt);

    return 0;
}
