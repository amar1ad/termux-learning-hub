#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Termux Database - SQL to JSON Exporter
Exports MySQL/MariaDB data to JSON format
Version: 1.0.0
"""

import json
import mysql.connector
from mysql.connector import Error
import sys
from datetime import datetime
from pathlib import Path

# Database Configuration
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '',  # Update with your password
    'database': 'termux_learning'
}

class TermuxDatabaseExporter:
    """Export Termux commands from SQL database to JSON"""
    
    def __init__(self, db_config):
        """Initialize database connection"""
        self.db_config = db_config
        self.connection = None
        self.cursor = None
    
    def connect(self):
        """Establish database connection"""
        try:
            self.connection = mysql.connector.connect(**self.db_config)
            self.cursor = self.connection.cursor(dictionary=True)
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
    
    def export_categories(self):
        """Export categories from database"""
        try:
            query = """
            SELECT id, title_ar, title_en, description_ar, description_en, 
                   icon, difficulty, `order`
            FROM categories
            ORDER BY `order`
            """
            self.cursor.execute(query)
            categories = []
            
            for row in self.cursor.fetchall():
                categories.append({
                    'id': row['id'],
                    'titleAr': row['title_ar'],
                    'titleEn': row['title_en'],
                    'descriptionAr': row['description_ar'],
                    'descriptionEn': row['description_en'],
                    'icon': row['icon'],
                    'difficulty': row['difficulty'],
                    'order': row['order']
                })
            
            print(f"✓ Exported {len(categories)} categories")
            return categories
        except Error as e:
            print(f"✗ Error exporting categories: {e}")
            return []
    
    def export_commands(self):
        """Export commands from database"""
        try:
            query = """
            SELECT id, category_id, title_ar, title_en, description_ar, 
                   description_en, command_syntax, difficulty
            FROM commands
            ORDER BY category_id, id
            """
            self.cursor.execute(query)
            commands = []
            
            for row in self.cursor.fetchall():
                command_id = row['id']
                
                # Get examples
                examples = self.export_examples(command_id)
                
                # Get tips
                tips = self.export_tips(command_id)
                
                # Get errors
                errors = self.export_errors(command_id)
                
                command = {
                    'id': command_id,
                    'categoryId': row['category_id'],
                    'titleAr': row['title_ar'],
                    'titleEn': row['title_en'],
                    'descriptionAr': row['description_ar'],
                    'descriptionEn': row['description_en'],
                    'command': row['command_syntax'],
                    'difficulty': row['difficulty']
                }
                
                if examples:
                    command['examples'] = examples
                if tips:
                    command['tips'] = tips
                if errors:
                    command['commonErrors'] = errors
                
                commands.append(command)
            
            print(f"✓ Exported {len(commands)} commands")
            return commands
        except Error as e:
            print(f"✗ Error exporting commands: {e}")
            return []
    
    def export_examples(self, command_id):
        """Export examples for a command"""
        try:
            query = """
            SELECT command_ar, command_en, description_ar, description_en
            FROM command_examples
            WHERE command_id = %s
            ORDER BY `order`
            """
            self.cursor.execute(query, (command_id,))
            examples = []
            
            for row in self.cursor.fetchall():
                examples.append({
                    'commandAr': row['command_ar'],
                    'commandEn': row['command_en'],
                    'descriptionAr': row['description_ar'],
                    'descriptionEn': row['description_en']
                })
            
            return examples
        except Error as e:
            print(f"✗ Error exporting examples: {e}")
            return []
    
    def export_tips(self, command_id):
        """Export tips for a command"""
        try:
            query = """
            SELECT tip_ar, tip_en
            FROM command_tips
            WHERE command_id = %s
            ORDER BY `order`
            """
            self.cursor.execute(query, (command_id,))
            tips = []
            
            for row in self.cursor.fetchall():
                tips.append({
                    'tipAr': row['tip_ar'],
                    'tipEn': row['tip_en']
                })
            
            return tips
        except Error as e:
            print(f"✗ Error exporting tips: {e}")
            return []
    
    def export_errors(self, command_id):
        """Export common errors for a command"""
        try:
            query = """
            SELECT error_ar, error_en, solution_ar, solution_en
            FROM command_errors
            WHERE command_id = %s
            ORDER BY `order`
            """
            self.cursor.execute(query, (command_id,))
            errors = []
            
            for row in self.cursor.fetchall():
                errors.append({
                    'errorAr': row['error_ar'],
                    'errorEn': row['error_en'],
                    'solutionAr': row['solution_ar'],
                    'solutionEn': row['solution_en']
                })
            
            return errors
        except Error as e:
            print(f"✗ Error exporting errors: {e}")
            return []
    
    def get_statistics(self):
        """Get database statistics"""
        try:
            stats = {}
            
            # Count categories
            self.cursor.execute("SELECT COUNT(*) as count FROM categories")
            stats['totalCategories'] = self.cursor.fetchone()['count']
            
            # Count commands
            self.cursor.execute("SELECT COUNT(*) as count FROM commands")
            stats['totalCommands'] = self.cursor.fetchone()['count']
            
            # Count examples
            self.cursor.execute("SELECT COUNT(*) as count FROM command_examples")
            stats['totalExamples'] = self.cursor.fetchone()['count']
            
            # Count tips
            self.cursor.execute("SELECT COUNT(*) as count FROM command_tips")
            stats['totalTips'] = self.cursor.fetchone()['count']
            
            # Count errors
            self.cursor.execute("SELECT COUNT(*) as count FROM command_errors")
            stats['totalErrors'] = self.cursor.fetchone()['count']
            
            return stats
        except Error as e:
            print(f"✗ Error getting statistics: {e}")
            return {}
    
    def export_all(self, output_file='termux-commands-export.json'):
        """Export all data to JSON file"""
        print("\n" + "="*60)
        print("Termux Database - SQL to JSON Exporter")
        print("="*60 + "\n")
        
        # Connect to database
        if not self.connect():
            return False
        
        try:
            # Export data
            categories = self.export_categories()
            commands = self.export_commands()
            stats = self.get_statistics()
            
            # Create JSON structure
            data = {
                'metadata': {
                    'version': '1.0.0',
                    'lastUpdated': datetime.now().isoformat(),
                    'language': 'ar-en',
                    'totalCategories': stats.get('totalCategories', 0),
                    'totalCommands': stats.get('totalCommands', 0),
                    'totalExamples': stats.get('totalExamples', 0),
                    'totalTips': stats.get('totalTips', 0),
                    'totalErrors': stats.get('totalErrors', 0),
                    'description': 'قاعدة بيانات شاملة لأوامر Termux - Comprehensive Termux Commands Database'
                },
                'categories': categories,
                'commands': commands
            }
            
            # Write to file
            with open(output_file, 'w', encoding='utf-8') as f:
                json.dump(data, f, ensure_ascii=False, indent=2)
            
            print(f"\n✓ Data exported to: {output_file}")
            print("\nExport Summary:")
            print(f"  - Categories: {stats.get('totalCategories', 0)}")
            print(f"  - Commands: {stats.get('totalCommands', 0)}")
            print(f"  - Examples: {stats.get('totalExamples', 0)}")
            print(f"  - Tips: {stats.get('totalTips', 0)}")
            print(f"  - Errors: {stats.get('totalErrors', 0)}")
            print("\n" + "="*60)
            print("✓ Export completed successfully!")
            print("="*60 + "\n")
            
            return True
        
        finally:
            self.disconnect()

def main():
    """Main function"""
    # Get output file path
    if len(sys.argv) > 1:
        output_file = sys.argv[1]
    else:
        output_file = 'termux-commands-export.json'
    
    # Create exporter and export data
    exporter = TermuxDatabaseExporter(DB_CONFIG)
    success = exporter.export_all(output_file)
    
    sys.exit(0 if success else 1)

if __name__ == '__main__':
    main()
