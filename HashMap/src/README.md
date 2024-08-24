# Design a HashMap

## Constrains and Assumptions

- Is this a **generic** HashMap? (any key/value pair)
  - Yes
- For collision resolution, are we using **chaining**?
  - Yes
- Is there a way to consider **load factor**?
  - Yes, in case load factor > 0.7, number of buckets is doubled
- Can we assume the inputs are valid?
  - Yes, they are valid
