# Virtual Parser

Virtual panel parser for Cellbase annotated variants. The aim is to replicate previous work, which used a list of gene 
symbols and a rule-set to return a list of possible pathogenic variants from Alamut annotated VCF data 
[variant_parser](https://github.com/pasted/variant_parser).
  
This implementation will include annotation via Cellbase from the original VCF. To keep connections to various RESTful 
services down the gene list is used to pre-filter the VCF based on the genomic co-ordinates returned from ENSEMBL Biomart.

Population frequency data should then be retrieved from Gnomad via Tabix, and the rule-set applied with the final output
being a list of candidate variants with additional annotation and reason for shortlisting.


## Usage

Uses HTSJDK to parse VCF files and submit the genomic coordinates to the Cellbase RESTful service for annotation.

Outline plan:

1. Ability to import VCF
2. Query Cellbase for annotation
3. Deconstruct returned JSON using Odin
4. Filter by ruleset and provided gene symbol list - check list against HGNC
5. Score and order shortlist by impact from Cellbase, population frequency from Gnomad

Additional future developments:

* Reactome integration - retrieve direct interacts with gene symbol products and use these to increase the size of the 
gene list
* Support for CNV calls - must be included as standard VCF (no custom data formats)
* Look at making ruleset configurable by introducing a DSL
* PanelApp / Monarch API integration - allow the user to provide a phenotype(s) and return the related gene symbols
* Support for multi-sample VCF and segregation analysis - will require a PED file for family structure

#### Current criterion plan:

Based on Katherine Smith's presentation of Genomics England Rare Disease Filtering rules (ACGS Exome Workshop 2017)
Rules adapted were required:

* Criterion 1: Variant must contain PASS filter (this maybe flagged to allow criterion to be relaxed)
* Criterion 2: Allele frequency 0.01 Biallelic / 0.001 Monoallelic (can be set by args) 
* Criterion 3:
     - Following Biotypes allowed: protein_coding; nonsense_mediated_decay; non_stop_decay;
     IG_C_gene; IG_D_gene; IG_J_gene; IG_V_gene; TR_C_gene; TR_D_gene; TR_J_gene; TR_V_gene
     - High consequence: transcript_ablation; splice_acceptor_variant; splice_donor_variant; 
        stop_gained; frameshift_variant; stop_lost; initiator_codon_variant
     - Moderate consequence: transcript_amplification; inframe_insertion; inframe_deletion; missense_variant;
        splice_region_variant; incomplete_terminal_codon_variant
* Criterion 4: Segregation (maybe future work since it will require a pedigree)
* Criterion 5: Gene panels (checked against HGNC api)

## License

Copyright © 2017 Garan Jones

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
