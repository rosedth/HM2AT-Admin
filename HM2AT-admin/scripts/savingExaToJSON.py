from managingJSON import retrieve_JSONfile,update_JSONfile, copySource
import sys
import os

# the file to be converted to json format
filename = sys.argv[1]

# the file to store json data
filename_out = sys.argv[2]

# the path of the repository
repository = sys.argv[3]

# retrieve information about implementation
dictionary = retrieve_JSONfile(filename)

# create implementation folder
exId = dictionary["id"]
exDir = os.path.join(repository, exId)
os.mkdir(exDir)


# copy sources and script
src=dictionary["sourcePath"]
copySource(src, exDir)

newSource = exDir +'\\'+ os.path.basename(src)
print("New Source:", newSource)

# update Json file
dictionary["sourcePath"] = newSource
update_JSONfile(filename,filename_out, dictionary)