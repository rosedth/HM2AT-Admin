from managingJSON import retrieve_JSONfile,update_JSONfile
import sys
import os
import xml.dom.minidom

# the file to be converted to json format
filename = sys.argv[1]

# the file to store json data
filename_out = sys.argv[2]

# the path of the repository
repository = sys.argv[3]

# retrieve information about implementation
dictionary = retrieve_JSONfile(filename)

# create implementation folder
depId = dictionary["id"]
depDir = os.path.join(repository, depId)
os.mkdir(depDir)


# creates pom 
pom_filename=os.path.join(depDir,"pom.xml")
text=dictionary["spec"]
dom = xml.dom.minidom.parseString(text)
xml_string = dom.toprettyxml()
part1, part2 = xml_string.split('?>')

with open(pom_filename, 'w') as xfile:
    xfile.write(part1 + 'encoding=\"{}\"?>\n'.format('UTF-8') + part2)
    xfile.close()

#src=dictionary["sourcePath"]
#copySource(src, depDir)
#os.remove(src)

#newSource = depDir +'\\'+ os.path.basename(src)
print("Source:", dictionary["sourcePath"])

# update Json file
#dictionary["sourcePath"] = newSource
update_JSONfile(filename,filename_out, dictionary)