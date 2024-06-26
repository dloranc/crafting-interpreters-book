CC = gcc
CFLAGS = -Wall -Wextra -g -MMD -MP -O3
SRCDIR = src
BINDIR = bin

SOURCES := $(wildcard $(SRCDIR)/*.c)
OBJECTS := $(SOURCES:$(SRCDIR)/%.c=$(BINDIR)/%.o)
DEPENDS := $(OBJECTS:.o=.d)
EXECUTABLE = $(BINDIR)/main

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(CFLAGS) $^ -o $@

$(BINDIR)/%.o: $(SRCDIR)/%.c | $(BINDIR)
	$(CC) $(CFLAGS) -c $< -o $@

$(BINDIR):
	mkdir -p $(BINDIR)

run: $(EXECUTABLE)
	./$(EXECUTABLE)

clean:
	rm -rf $(BINDIR)

-include $(DEPENDS)
