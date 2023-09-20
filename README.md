# Ubicomp/ISWC_2023_Late-Breaking-Work

## WatchPPG: An Open-Source Toolkit for PPG-based Stress Detection using Off-the-shelf Smartwatches

The recent popularity of commercial wrist wearables has made it possible to study stress intervention systems in the wild, but there is a lack of pragmatic platforms for research prototyping and evaluation. We present an open-source toolkit for collecting raw photoplethysmography (PPG) data and modeling stress detection using Samsung Galaxy Watch, an off-the-shelf smartwatch. The feasibility of the toolkit for stress detection was validated against existing wearables such as Polar H10 and Empatica E4.

WatchPPG consists of 
1. a WearOS application on Samsung Galaxy Watch for collecting raw PPG data
2. a real-time stress detection pipeline. 
  
Below figure illustrates our PPG-based stress detection pipeline.

<img src="https://github.com/yjhan99/Ubicomp_2023_LBW/blob/main/pipeline.png?raw=true" width="450">

## More Information
The pipeline takes a PPG signal and ouputs time and frequency domain heart rate variability (HRV) features commonly used in related researches:

Time-domain:
* the mean peak-to-peak (PP) intervals (mean PP)
* beats per minute (BPM)
* the standard deviation of the PP (SDNN)
* the root mean square of the successive differences of PP (RMSSD)
* the proportion calculated by dividing the number of interval differences of successive PP greater than 50 ms by the total number of PP (PNN50)

Frequecy-domain:
* low-frequency band (LF)
* high-frequency band (HF)
* ratio LF/HF (LF/HF)
