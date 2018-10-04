# SamplingAlgorithms

Configuration sampling algorithms with constraints. Till now, we have collected the following <b>9</b> sampling strategies. 


### 1. One-enabled Sampling

**One-enabled** sampling strategy enables one features at each time and disables the other features with the constraints. 
For features A, B, C and D we can get 4 samples like that, 

```
[A, !B, !C, !D]
[!A, B, !C, !D]
[!A, !B, C, !D]
[!A, !B, !C, D]
```


### 2. One-disabled Sampling

**One-disabled** sampling strategy disables one features at each time and enables the other features with the constraints. 
For features A, B, C and D we can get 4 samples like that, 

```
[!A, B, C, D]
[A, !B, C, D]
[A, B, !C, D]
[A, B, C, !D]
```


### 3. Most-enabled-disabled Sampling

**Most-enabled-disabled** collects 2 configurations which either has the most enabled features or has the most disabled features. 
For features A, B, C and D we can get 2 samples like that, 

```
[!A, !B, !C, !D]
[A, B, C, D]
```


### 4. All-one-enabled Sampling

**All-one-enabled** sampling strategy enables one features at each time and disables the other features with the constraints. 
Note: **All-one-enabled** strategy differs from **One-enabled** strategy that the former collects all valid configurations while the latter only obtains the first satisfied one.


### 5. All-one-disabled Sampling

**All-one-disabled** sampling strategy disables one features at each time and enables the other features with the constraints. 
Note: **All-one-enabled** strategy differs from **One-disabled** strategy that the former collects all valid configurations while the latter only obtains the first satisfied one.


### 6. All-most-enabled-disabled Sampling

**All-most-enabled-disabled** collects 2 kinds of configurations which either has the most enabled features or has the most disabled features. 
Note: **All-most-enabled-disabled** strategy differs from **Most-enabled-disabled** strategy that the former collects all valid configurations while the latter only obtains the first satisfied one.


### 7. Random Sampling

**Random** sampling strategy randomly selects several configurations that satisfied constraints, which can be considered as a robust baseline strategy. 

Note: this strategy needs the variable **num** (configurations to be selected).


### 8. T-wise Sampling

**T-wise** sampling strategy generates configurations to cover all the combination among **T** features.
Note: Due to the limitation of computation, we only choose **T=1, 2, 3, 4**. The dependent tool we used for **T-wise** algorithm is [SPLCAT](https://martinfjohansen.com/splcatool/), which can be found under `lib/` directory.
 
 
### 9. Dissimilarity Sampling

**Dissimilarity** sampling strategy uses evolutionary algorithm to generate an optimal configurations to cover the feature interaction of **T-wise** strategy. The biggest advantage of **Dissimilarity** strategy is that it generates configurations in a heuristic way which can avoid the expensive computation in **T-wise** strategy. 
The dependent tool we used for **Dissimilarity** algorithm is [PLEDGE](https://github.com/christopherhenard/pledge), which can be found under `lib/` directory.

Note: this strategy needs the variable **num** (configurations to be selected).

