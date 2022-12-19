# Device-Scheduling

## Project Title:
Simulation and optimization of Medical Devices in Hospitals, a batch scheduling approach.

## Problem Description:
A Parallel Batch Scheduling Problem.
The problem consists of non-identical jobs with different sizes and arrival times and multiple machines working in parallel with the same capacity and processing time. 
Multiple jobs can be batched together and scheduled on machines as long as the summation of the job sizes in a batch does not exceed the machine capacity. 

There exists a set of N jobs, J = {1,2,...,N}, to be processed on M identical parallel batch processing machines, m ∈ {1,2,...,M}. 
For each job j ∈ J the job size (s_j), job release date (r_j) are known in advance for the offline cases, and known at the time of arrival for the online cases, all jobs have the same job processing time (p). 
Job release date is the time that job arrives at the washing step and becomes ready for washing, which is the earliest time that the job can start processing. 
Jobs have unidentical sizes and release dates. 
Each machine m  has a maximum capacity C and processing time p. 
All machines are identical, and their processing time is equal to the job processing time. 
Each machine can process multiple jobs in batches as long as the total size of all the jobs on the machine does not exceed the machine capacity (C). 
Inspired by Graham’s notation (Graham 1979), the following notation is used for this problem: P|p-batch,r_j,p_j=p,s_j,C| C_max. P refers to identical parallel machines, p-batch indicates parallel batching, p_j=p shows identical processing time, r_j  and s_j  demonstrates job release time and job size, C is for machine (batch) capacity and C_max refers to the makespan.

## Assumptions:
-	Splitting jobs into different batches is not allowed.
- Machines cannot be interrupted during the process.
- Machine capacity is equal to batch capacity.
- Total job sizes on a batch should not exceed machine capacity.
- All jobs have the same processing time p.

## This repository has the following compounents:
  1. Scheduling and Optimization Algorithms with CPLEX in Java (Optimal Approach)
  2. Simulation Models for Scheduling Medical Devices (Simulation Heuristics)
  3. Dynamic programming Heuristic for the problem (Mathematical Heuristic)
