package com.example.yehoon.sorting_show;

import java.util.Arrays;

public class RadixSort extends VisualizerController{
    @Override
    public void run() {
        super.run();
    }

    private int[] a;
    private int n;  //length of array;



    // A utility function to get maximum value in arr[]
    private int getMax(int arr[], int n)
    {
        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx){
                mx = arr[i];
        }
        addLog("Finding maximum number - Maximum value : "+mx);
        sleep();
        return mx;
    }

    // A function to do counting sort of arr[] according to
    // the digit represented by exp.
    void countSort(int arr[], int n, int exp)
    {
        int output[] = new int[n]; // output array
        int i;
        int count[] = new int[10];
        Arrays.fill(count,0);

        // Store count of occurrences in count[]
        for (i = 0; i < n; i++){
            count[ (arr[i]/exp)%10 ]++;
            addLog("Count of "+i+ " is now "+count[(arr[i]/exp)%10]);
            sleep();
        }

        // Change count[i] so that count[i] now contains
        // actual position of this digit in output[]
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array
        for (i = n - 1; i >= 0; i--)
        {
            output[count[ (arr[i]/exp)%10 ] - 1] = arr[i];
            count[ (arr[i]/exp)%10 ]--;
        }

        // Copy the output array to arr[], so that arr[] now
        // contains sorted numbers according to curent digit
        for (i = 0; i < n; i++){
            highlightTrace(arr[i]);
            highlightTrace(output[i]);
            arr[i] = output[i];
            highlightSwap(arr[i], output[i]);
            sleep();

        }

    }

    // The main function to that sorts arr[] of size n using
    // Radix Sort
    void radixsort(int arr[], int n)
    {

        // Find the maximum number to know number of digits
        int m = getMax(arr, n);

        // Do counting sort for every digit. Note that instead
        // of passing digit number, exp is passed. exp is 10^i
        // where i is current digit number
        for (int exp = 1; m/exp > 0; exp *= 10){
            addLog("currently sorting the " +exp+"'s digit");
            countSort(arr, n, exp);
        }
    }

    @Override
    public void onDataRecieved(Object data) {
        super.onDataRecieved(data);
        this.a = ((DataSet) data).arr;
        this.n = ((DataSet) data).n;
    }

    @Override
    public void onMessageReceived(String message) {
        super.onMessageReceived(message);
        if (message.equals(VisualizerController.COMMAND_START_ALGORITHM)) {
            startExecution();
            logArray("Original array - ", a);
            radixsort(a, n);
            addLog("Array has been sorted");
            completed();
        }
    }
}





