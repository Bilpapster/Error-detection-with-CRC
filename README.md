# Error detection with <a href="https://en.wikipedia.org/wiki/Cyclic_redundancy_check">Cyclic Redundancy Check (CRC)</a> 

  This repository hosts Java code for a CRC-based system. The system simulates transmissions of random digital messages between two imaginary points, a sending and a receiving one. CRC is executed on both ends, in order to ensure successful delivery over a noisy communication channel. The following steps describe in natural language and in short the typical workflow of the simulator:

<ol>
  <li>Generation of the random message (at the sending end)</li>
  <li>Frame Check Sequence appendix (at the sending end)</li>
  <li>Message transmission (via a noisy channel)</li>
  <li>Error checking with CRC (at the receiving end)</li>
  <li>Execution data records (at the receiving end)</li>
</ol>

  The hosted code was genuinely developed by the author, for the purposes of an undergraduate university assignment in the course of "Digital Communications". Apart from the developed Java code, the assignment report can be also found in the current repository. Feel free to contact the author for any comments or ideas. 
