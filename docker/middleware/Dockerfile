FROM openjdk:8

WORKDIR /

RUN mkdir logs

COPY files/bookshop.jar /
COPY files/startup.sh /
COPY files/wait-for-it.sh /
    
RUN /bin/bash -c 'chmod +x startup.sh wait-for-it.sh'

CMD ["./startup.sh"]


