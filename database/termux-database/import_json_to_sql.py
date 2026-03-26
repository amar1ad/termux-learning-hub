#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Termux Database - JSON to SQL Importer
Imports JSON data into MySQL/MariaDB database
Version: 1.0.0
"""

import json
import mysql.connector
from mysql.connector import Error
import sys
from pathlib import Path

# Database Configuration
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '',  # Update with your password
    'database': 'termux_learning'
}

class TermuxDatabaseImporter:
    """Import Termux commands from JSON to SQL database"""
    
    def __init__(self, db_config):
        """Initialize database connection"""
        self.db_config = db_config
        self.connection = None
        self.cursor = None
    
    def connect(self):
        """Establish database connection"""
        try:
            self.connection = mysql.connector.connect(**self.db_config)
            self.cursor = self.connection.cursor()
            print("✓ Database connection established")
            return True
        except Error as e:
            print(f"✗ Database connection error: {e}")
            return False
    
    def disconnect(self):
        """Close database connection"""
        if self.cursor:
            self.cursor.close()
        if self.connection:
            self.connection.close()
        print("✓ Database connection closed")
    
    def load_json(self, json_file):
        """Load JSON data from file"""
        try:
            with open(json_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            print(f"✓ JSON file loaded: {json_file}")
            return data
        except Exception as e:
            print(f"✗ Error loading JSON: {e}")
            return None
    
    def import_categories(self, categories):
        """Import categories into database"""
        try:
            query = """
            INSERT INTO categories 
            (id, title_ar, title_en, description_ar, description_en, icon, difficulty, `order`)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            ON DUPLICATE KEY UPDATE
            title_ar = VALUES(title_ar),
            title_en = VALUES(title_en),
            description_ar = VALUES(description_ar),
            description_en = VALUES(description_en),
            icon = VALUES(icon),
            difficulty = VALUES(difficulty),
            `order` = VALUES(`order`)
            """
            
            count = 0
            for category in categories:
                self.cursor.execute(query, (
                    category['id'],
                    category['titleAr'],
                    category['titleEn'],
                    category['descriptionAr'],
                    category['descriptionEn'],
                    category['icon'],
                    category['difficulty'],
                    category['order']
                ))
                count += 1
            
            self.connection.commit()
            print(f"✓ Imported {count} categories")
            return True
        except Error as e:
            print(f"✗ Error importing categories: {e}")
            self.connection.rollback()
            return False
    
    def import_commands(self, commands):
        """Import commands into database"""
        try:
            query = """
            INSERT INTO commands 
            (id, category_id, title_ar, title_en, description_ar, description_en, command_syntax, difficulty)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            ON DUPLICATE KEY UPDATE
            title_ar = VALUES(title_ar),
            title_en = VALUES(title_en),
            description_ar = VALUES(description_ar),
            description_en = VALUES(description_en),
            command_syntax = VALUES(command_syntax),
            difficulty = VALUES(difficulty)
            """
            
            count = 0
            for command in commands:
                self.cursor.execute(query, (
                    command['id'],
                    command['categoryId'],
                    command['titleAr'],
                    command['titleEn'],
                    command['descriptionAr'],
                    command['descriptionEn'],
                    command['command'],
                    command['difficulty']
                ))
                count += 1
            
            self.connection.commit()
            print(f"✓ Imported {count} commands")
            return True
        except Error as e:
            print(f"✗ Error importing commands: {e}")
            self.connection.rollback()
            return False
    
    def import_examples(self, commands):
        """Import command examples into database"""
        try:
            query = """
            INSERT INTO command_examples 
            (command_id, command_ar, command_en, description_ar, description_en, `order`)
            VALUES (%s, %s, %s, %s, %s, %s)
            """
            
            count = 0
            for command in commands:
                if 'examples' in command:
                    for idx, example in enumerate(command['examples'], 1):
                        self.cursor.execute(query, (
                            command['id'],
                            example['commandAr'],
                            example['commandEn'],
                            example['descriptionAr'],
                            example['descriptionEn'],
                            idx
                        ))
                        count += 1
            
            self.connection.commit()
            print(f"✓ Imported {count} command examples")
            return True
        except Error as e:
            print(f"✗ Error importing examples: {e}")
            self.connection.rollback()
            return False
    
    def import_tips(self, commands):
        """Import command tips into database"""
        try:
            query = """
            INSERT INTO command_tips 
            (command_id, tip_ar, tip_en, `order`)
            VALUES (%s, %s, %s, %s)
            """
            
            count = 0
            for command in commands:
                if 'tips' in command:
                    for idx, tip in enumerate(command['tips'], 1):
                        self.cursor.execute(query, (
                            command['id'],
                            tip['tipAr'],
                            tip['tipEn'],
                            idx
                        ))
                        count += 1
            
            self.connection.commit()
            print(f"✓ Imported {count} command tips")
            return True
        except Error as e:
            print(f"✗ Error importing tips: {e}")
            self.connection.rollback()
            return False
    
    def import_errors(self, commands):
        """Import common errors into database"""
        try:
            query = """
            INSERT INTO command_errors 
            (command_id, error_ar, error_en, solution_ar, solution_en, `order`)
            VALUES (%s, %s, %s, %s, %s, %s)
            """
            
            count = 0
            for command in commands:
                if 'commonErrors' in command:
                    for idx, error in enumerate(command['commonErrors'], 1):
                        self.cursor.execute(query, (
                            command['id'],
                            error['errorAr'],
                            error['errorEn'],
                            error['solutionAr'],
                            error['solutionEn'],
                            idx
                        ))
                        count += 1
            
            self.connection.commit()
            print(f"✓ Imported {count} common errors")
            return True
        except Error as e:
            print(f"✗ Error importing errors: {e}")
            self.connection.rollback()
            return False
    
    def import_all(self, json_file):
        """Import all data from JSON file"""
        print("\n" + "="*60)
        print("Termux Database - JSON to SQL Importer")
        print("="*60 + "\n")
        
        # Load JSON
        data = self.load_json(json_file)
        if not data:
            return False
        
        # Connect to database
        if not self.connect():
            return False
        
        try:
            # Import data in order
            if not self.import_categories(data.get('categories', [])):
                return False
            
            if not self.import_commands(data.get('commands', [])):
                return False
            
            if not self.import_examples(data.get('commands', [])):
                return False
            
            if not self.import_tips(data.get('commands', [])):
                return False
            
            if not self.import_errors(data.get('commands', [])):
                return False
            
            print("\n" + "="*60)
            print("✓ Import completed successfully!")
            print("="*60 + "\n")
            return True
        
        finally:
            self.disconnect()

def main():
    """Main function"""
    # Get JSON file path
    if len(sys.argv) > 1:
        json_file = sys.argv[1]
    else:
        json_file = 'termux-commands.json'
    
    # Check if file exists
    if not Path(json_file).exists():
        print(f"✗ Error: File not found: {json_file}")
        sys.exit(1)
    
    # Create importer and import data
    importer = TermuxDatabaseImporter(DB_CONFIG)
    success = importer.import_all(json_file)
    
    sys.exit(0 if success else 1)

if __name__ == '__main__':
    main()
