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
impId = dictionary["id"]
impDir = os.path.join(repository, impId)
os.mkdir(impDir)


# copy sources and script
src=dictionary["sourcePath"]
copySource(src, impDir)

newSource = impDir +'\\'+ os.path.basename(src)
print("New Source:", newSource)

scpt=dictionary["script"]
copySource(scpt, impDir)
newScript = impDir +'\\'+ os.path.basename(scpt)
print("New Script:", newScript)

# update Json file
dictionary["sourcePath"] = newSource
dictionary["script"] = newScript
update_JSONfile(filename,filename_out, dictionary)