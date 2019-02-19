# pseudo:
# opt = []
# opt.append(0)
# for i in range(1, len(lines)):
#     for j in range(0, i-1):
#         opt[i] = min(opt[j] + pow((400 - (lines[j]-lines[i])),2))

fileName = '/Users/simongrac/Documents/Škola/STU/8.semester/ADS/2018/cvicenie2data2019.txt'

with open(fileName, 'r') as input_file:
    stops = []
    for line in input_file:
        stops.append(int(line.strip()))

opt_jump = 400
best_path = {}
stop = {}

for i in range(0, len(stops)):
    best_path[i] = pow(opt_jump - stops[i], 2)
    stop[i] = 0
    for j in range(0, i):
        if best_path[j] + pow((opt_jump - (stops[i] - stops[j])), 2) < best_path[i]:
            best_path[i] = best_path[j] + pow((opt_jump - (stops[i] - stops[j])), 2)
            stop[i] = j + 1

print("Minimal penalty: ", best_path[len(stops)-1])

final_path = ""
index = len(stop) - 1

while index >= 0:
    final_path = str((index + 1)) + " " + final_path
    index = stop[index] - 1

#print("Stop: ", stop)
print("Stop at: ",final_path)



