FROM continuumio/miniconda3:24.5.0-0
WORKDIR /home/jasminesv
RUN conda config --add channels bioconda && \
    conda config --add channels conda-forge && \
    conda install -y jasminesv=1.1.5 bcftools=1.20

COPY jasmine.jar jasmine_igv.jar jasmine_iris.jar Iris/iris.jar /opt/conda/bin/
COPY jasmine.jar jasmine_igv.jar jasmine_iris.jar Iris/iris.jar /opt/conda/pkgs/jasminesv-1.1.5-hdfd78af_0/bin/

CMD [ "/bin/bash" ]
