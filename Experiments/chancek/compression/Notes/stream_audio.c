#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
	FILE *fid = fopen(argv[1], "rb");
	int mp3_content[20];
	fread(mp3_content, sizeof(int), 5, fid);
	fclose(fid);

	for(int i=0; i<5; i++){
		printf("%d\n", mp3_content[i]);
	}

	// fid = fopen('OutputFile.mp3', 'w');
	// fwrite(fid, TheVectorOfUint8);
	// fclose(fid);
	return 0;
}

