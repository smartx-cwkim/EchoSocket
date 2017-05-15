#!/bin/bash
for i in $(seq 0 2) 
do
	docker stop xxx$i
	docker rm xxx$i

done

exit 0

