from managingJSON import retrieve_JSONfile,update_JSONfile, copySource
import sys

# the file to be converted to json format
filename = sys.argv[1]

# the file to store json data
filename_out=sys.argv[2]

#retrievingJSON2.retrieve_JSONfileProcess(filename, filename_out,"models")
dictionary=retrieve_JSONfile(filename, filename_out)
update_JSONfile(filename, filename_out, dictionary,"models")
src=dictionary["sourcePath"]
dst="D:\Academics\PhD\workspaces\python\scripts\sample\models"
copySource(src, dst)