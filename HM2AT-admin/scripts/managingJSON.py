# Python program to convert text
# file to JSON
 
 
import json
import sys
import os
import shutil


def update_JSONfile(filename,filename_out,dictionary):
#    dictionary = retrieve_JSONfile(filename, filename_out, propertyName)
    if  os.path.exists(filename_out):
        # loading the json file
        with open(filename_out,'r+') as file:
            file_data = json.load(file)
            file_data.append(dictionary)
            file.seek(0)
            json.dump(file_data, file, indent = 4)
    else:
        # creating the json file
        data={}
        models=[]
        models.append(dictionary)
        data=models
        with open(sys.argv[2], 'w') as file:
            json.dump(data, file,indent=4, sort_keys = False)
    file.close()
    # removing temp file
    os.remove(filename)
    
def retrieve_JSONfile(filename):
    # dictionary where the lines from text will be stored
    dictionary = {}
     
    # creating dictionary
    with open(filename) as fh:
     
        for line in fh:
            # reads each line and read key,value pair
            key, value = line.strip().split(None, 1)
            dictionary[key] = value.strip()
    fh.close()
    return dictionary
    
def copySource(src, dst):
    return shutil.copy2(src, dst) 