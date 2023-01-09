# Python program to convert text
# file to JSON
 
 
import json
import sys
import os
import shutil

def retrieve_JSONfileProcess(filename,filename_out,propertyName):
    # dictionary where the lines from text will be stored
    dictionary = {}
     
    # creating dictionary
    with open(filename) as fh:
     
        for line in fh:
            # reads each line and read key,value pair
            key, value = line.strip().split(None, 1)
            dictionary[key] = value.strip()
    fh.close()
    
    
    if  os.path.exists(filename_out):
        # loading the json file
        with open(filename_out,'r+') as file:
            file_data = json.load(file)
            file_data[propertyName].append(dictionary)
            file.seek(0)
            json.dump(file_data, file, indent = 4)
    else:
        # creating the json file
        data={}
        models=[]
        models.append(dictionary)
        data[propertyName]=models
        with open(sys.argv[2], 'w') as file:
            json.dump(data, file,indent=4, sort_keys = False)
    file.close()
    
    # removing temp file
    os.remove(filename)
    
    #copy the file in sourecepath
    src=dictionary["sourcePath"]
    dst="D:\Academics\PhD\workspaces\python\scripts\sample\models"
    shutil.copy2(src, dst) 
    
    #update indexes
    
    return file

def update_JSONfile(filename,filename_out,dictionary,propertyName):
#    dictionary = retrieve_JSONfile(filename, filename_out, propertyName)
    if  os.path.exists(filename_out):
        # loading the json file
        with open(filename_out,'r+') as file:
            file_data = json.load(file)
            file_data[propertyName].append(dictionary)
            file.seek(0)
            json.dump(file_data, file, indent = 4)
    else:
        # creating the json file
        data={}
        models=[]
        models.append(dictionary)
        data[propertyName]=models
        with open(sys.argv[2], 'w') as file:
            json.dump(data, file,indent=4, sort_keys = False)
    file.close()
    # removing temp file
    os.remove(filename)
    
def retrieve_JSONfile(filename,filename_out):
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
    shutil.copy2(src, dst) 